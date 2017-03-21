import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TestOrganCalc {
	
	static int count = 0;

	public static void main(String[] args) {

		List<Long> finalOrganList = calcHighestOrgan(initAllOrgans(), initSelectedOrgan());

		for (Long organId : finalOrganList) {
			System.out.print(organId + " ");
		}
	}

	public static Map<Long, CompanyOrgan> initAllOrgans() {

		// Level 1
		CompanyOrgan organ1 = new CompanyOrgan(1L, 0L, 1, false);

		// Level 2
		CompanyOrgan organ2 = new CompanyOrgan(2L, 1L, 2, false);
		CompanyOrgan organ3 = new CompanyOrgan(3L, 1L, 2, false);
		CompanyOrgan organ4 = new CompanyOrgan(4L, 1L, 2, false);

		// Level 3
		CompanyOrgan organ5 = new CompanyOrgan(5L, 2L, 3, false);
		CompanyOrgan organ6 = new CompanyOrgan(6L, 2L, 3, false);

		CompanyOrgan organ7 = new CompanyOrgan(7L, 3L, 3, true);

		CompanyOrgan organ8 = new CompanyOrgan(8L, 4L, 3, false);
		CompanyOrgan organ9 = new CompanyOrgan(9L, 4L, 3, false);
		CompanyOrgan organ10 = new CompanyOrgan(10L, 4L, 3, false);

		// Level 4
		CompanyOrgan organ11 = new CompanyOrgan(11L, 5L, 4, true);

		CompanyOrgan organ12 = new CompanyOrgan(12L, 6L, 4, true);
		CompanyOrgan organ13 = new CompanyOrgan(13L, 6L, 4, true);

		CompanyOrgan organ14 = new CompanyOrgan(14L, 8L, 4, true);

		CompanyOrgan organ15 = new CompanyOrgan(15L, 9L, 4, true);
		CompanyOrgan organ16 = new CompanyOrgan(16L, 9L, 4, true);

		CompanyOrgan organ17 = new CompanyOrgan(17L, 10L, 4, true);
		CompanyOrgan organ18 = new CompanyOrgan(18L, 10L, 4, true);
		CompanyOrgan organ19 = new CompanyOrgan(19L, 10L, 4, true);
		CompanyOrgan organ20 = new CompanyOrgan(20L, 10L, 4, true);

		Map<Long, CompanyOrgan> organMap = new HashMap<Long, CompanyOrgan>();
		organMap.put(1L, organ1);
		organMap.put(2L, organ2);
		organMap.put(3L, organ3);
		organMap.put(4L, organ4);
		organMap.put(5L, organ5);
		organMap.put(6L, organ6);
		organMap.put(7L, organ7);
		organMap.put(8L, organ8);
		organMap.put(9L, organ9);
		organMap.put(10L, organ10);
		organMap.put(11L, organ11);
		organMap.put(12L, organ12);
		organMap.put(13L, organ13);
		organMap.put(14L, organ14);
		organMap.put(15L, organ15);
		organMap.put(16L, organ16);
		organMap.put(17L, organ17);
		organMap.put(18L, organ18);
		organMap.put(19L, organ19);
		organMap.put(20L, organ20);

		return organMap;

	}

	public static List<Long> initSelectedOrgan() {

		List<Long> selectedOrganList = new ArrayList<Long>();

		selectedOrganList.add(1L);
		selectedOrganList.add(2L);
		selectedOrganList.add(3L);
		selectedOrganList.add(4L);
		selectedOrganList.add(5L);
		selectedOrganList.add(6L);
		selectedOrganList.add(7L);
		selectedOrganList.add(8L);
		selectedOrganList.add(9L);
		selectedOrganList.add(10L);
		selectedOrganList.add(11L);
		selectedOrganList.add(12L);
		selectedOrganList.add(13L);
		selectedOrganList.add(14L);
		selectedOrganList.add(15L);
		selectedOrganList.add(16L);
		selectedOrganList.add(17L);
		selectedOrganList.add(18L);
		selectedOrganList.add(19L);
		selectedOrganList.add(20L);

		return selectedOrganList;
	}

	public static List<Long> calcHighestOrgan(Map<Long, CompanyOrgan> allOrganMap, List<Long> selectedOrganList) {

		List<Long> finalOrganList = new ArrayList<Long>();

		Map<Long, Set<Long>> selectedOrganMap = new HashMap<Long, Set<Long>>();

		SortedSet<CompanyOrgan> sortedOrganSet = new TreeSet<CompanyOrgan>(new Comparator<CompanyOrgan>() {

			@Override
			public int compare(CompanyOrgan o1, CompanyOrgan o2) {
				count++;
				if (o1.getLevel() > o2.getLevel()) {
					return -1;
				} else {
					return 1;
				}

			}
		});

		for (Long selectedOrganId : selectedOrganList) {
			count++;
			CompanyOrgan selectedOrgan = allOrganMap.get(selectedOrganId);

			if (selectedOrgan.getLevel() > 1) {
				if (!selectedOrganMap.containsKey(selectedOrgan.getParentOrganId())) {
					selectedOrganMap.put(selectedOrgan.getParentOrganId(), new HashSet<Long>());
				}

				selectedOrganMap.get(selectedOrgan.getParentOrganId()).add(selectedOrgan.getOrganId());
			}

			if (!selectedOrgan.isLeaf()) {
				// sort organ order by desc
				sortedOrganSet.add(selectedOrgan);

				if (!selectedOrganMap.containsKey(selectedOrgan.getOrganId())) {
					selectedOrganMap.put(selectedOrgan.getOrganId(), new HashSet<Long>());
				}
			}

		}

		for (CompanyOrgan sortedOrgan : sortedOrganSet) {

			Set<Long> subOrganSet = selectedOrganMap.get(sortedOrgan.getOrganId());

			int totalSubCount = calcSubOrganCount(sortedOrgan.getOrganId());

			int subCount = 0;
			List<Long> filtedSubOrganList = new ArrayList<Long>();
			for (Long subOrganId : subOrganSet) {
				count++;
				CompanyOrgan subOrgan = allOrganMap.get(subOrganId);

				if (!subOrgan.isLeaf()) {
					if (selectedOrganMap.containsKey(subOrgan.getOrganId())) {
						subCount++;
						filtedSubOrganList.add(subOrganId);
					}
				} else {
					subCount++;
					filtedSubOrganList.add(subOrganId);
				}
			}

			if (totalSubCount != subCount) {
				selectedOrganMap.remove(sortedOrgan.getOrganId());
				finalOrganList.addAll(filtedSubOrganList);
			}

			if (sortedOrgan.getLevel() == 1 && totalSubCount == subCount) {
				finalOrganList.add(sortedOrgan.getOrganId());
			}
		}
		
		System.out.println(count);
		return finalOrganList;
	}

	private static int calcSubOrganCount(Long organId) {
		if (organId == 1) {
			return 3;
		} else if (organId == 2) {
			return 2;
		} else if (organId == 3) {
			return 1;
		} else if (organId == 4) {
			return 3;
		} else if (organId == 5) {
			return 1;
		} else if (organId == 6) {
			return 2;
		} else if (organId == 8) {
			return 1;
		} else if (organId == 9) {
			return 2;
		} else if (organId == 10) {
			return 4;
		}

		return 0;
	}

}
