package cn.net.zhaozhiwen.demo.springdata.persistence.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SpecifiactionUtils {
	public static Predicate like(CriteriaBuilder cb, Path<String> path,
			String str) {
		if (StringUtils.isEmpty(str))
			return truePredicate(cb);
		else
			return cb.like(path, "%" + str + "%");
	}

	public static Predicate truePredicate(CriteriaBuilder cb) {
		return cb.equal(cb.literal(1), cb.literal(1));
	}

	public static Predicate falsePredicate(CriteriaBuilder cb) {
		return cb.equal(cb.literal(1), cb.literal(0));
	}

	public static Collection<Long> getIds(String idStr) {
		String[] idArray = idStr.split(",");
		Set<Long> ids = new HashSet<Long>(idArray.length);
		for (String id : idArray)
			if (StringUtils.hasText(id))
				ids.add(Long.parseLong(id));

		return ids;
	}

	public static Predicate timeBetween(CriteriaBuilder cb, Path<Date> path,
			Date startTime, Date endTime) {
		Predicate startTimePredicate = null;
		if (startTime == null)
			startTimePredicate = SpecifiactionUtils.truePredicate(cb);
		else
			startTimePredicate = cb.greaterThanOrEqualTo(path, startTime);

		Predicate endTimePredicate = null;
		if (endTime == null)
			endTimePredicate = SpecifiactionUtils.truePredicate(cb);
		else
			endTimePredicate = cb.lessThanOrEqualTo(path, endTime);

		return cb.and(startTimePredicate, endTimePredicate);
	}
	
	@SuppressWarnings("rawtypes")
	private static From from(Map<String, From> joinMap, From join, String fullName, String absName) {
		if(joinMap.containsKey(fullName)) {
			join = joinMap.get(fullName);
		} else {
			join = join.join(absName);
			joinMap.put(fullName, join);
		}
		return join;
	}
	
	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
		return new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters!=null && !filters.isEmpty()) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					Map<String, From> joinMap = new HashMap<String, From>();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = filter.fieldName.split("[.]");
						Path expression = null;
						if(names.length==1) {
							expression = root.get(names[0]);
						} else {
							From join = root;
							String fullName = "";
							
							for (int i = 0; i < names.length-1; i++) {
								String absName = names[i];
								fullName += (i==0?"":".") + absName;
								join = from(joinMap, join, fullName, absName);
							}
							expression = join.get(names[names.length-1]);
						}

						// logic operator
						switch (filter.operator) {
						case EQ:
							predicates.add(builder.equal(expression, filter.value));
							break;
						case LIKE:
							predicates.add(builder.like(expression, "%" + filter.value + "%"));
							break;
						case GT:
							predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
							break;
						case LT:
							predicates.add(builder.lessThan(expression, (Comparable) filter.value));
							break;
						case GTE:
							predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case LTE:
							predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
							break;
						case IN:
							predicates.add(builder.in(expression).value((Collection) filter.value));
							break;
						}
					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}
		};
	}
}
