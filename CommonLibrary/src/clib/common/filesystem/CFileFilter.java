package clib.common.filesystem;

import java.util.ArrayList;
import java.util.List;

import clib.common.string.CStringMatcher;

public class CFileFilter {

	public static final CFileFilter ALL_ACCEPT_FILTER() {
		return new CFileFilter(true, false);
	}

	public static final CFileFilter ALL_IGNORE_FILTER() {
		return new CFileFilter(false, false);
	}

	public static final CFileFilter ACCEPT_BY_NAME_FILTER(String... names) {
		return new CFileFilter(false, false, names);
	}

	public static final CFileFilter ACCEPT_BY_EXTENSION_FILTER(
			String... extensions) {
		return new CFileFilter(false, true, extensions);
	}

	public static final CFileFilter IGNORE_BY_NAME_FILTER(String... names) {
		return new CFileFilter(true, false, names);
	}

	public static final CFileFilter IGNORE_BY_EXTENSION_FILTER(
			String... extensions) {
		return new CFileFilter(true, true, extensions);
	}

	private List<String> filters = new ArrayList<String>();
	private boolean ignoreMode = false;
	private boolean extensionMode = false;

	private CFileFilter(boolean ignoreMode, boolean extensionMode,
			String... filters) {
		this.ignoreMode = ignoreMode;
		this.extensionMode = extensionMode;
		add(filters);
	}

	public void add(String... filters) {
		for (int i = 0; i < filters.length; i++) {
			add(filters[i]);
		}
	}

	public void add(String filter) {
		this.filters.add(filter);
	}

	public boolean accept(CFileElement element) {
		return acceptInternal(element.getAbsolutePath());
	}

	private boolean acceptInternal(CPath path) {
		String target = null;
		if (extensionMode) {
			target = path.getName().getExtension();
		} else {
			target = path.getName().toString();
		}

		for (String filter : filters) {
			if (CStringMatcher.wildcardMatches(target, filter)) {
				return ignoreMode ? false : true;
			}
		}
		return ignoreMode ? true : false;
	}

	public static void main(String[] args) {
		CFileFilter filter;

		filter = ALL_ACCEPT_FILTER();
		test(filter, "a.a", true);
		test(filter, "a.b", true);
		test(filter, "b.a", true);

		filter = ALL_IGNORE_FILTER();
		test(filter, "a.a", false);
		test(filter, "a.b", false);
		test(filter, "b.a", false);

		filter = ACCEPT_BY_NAME_FILTER();
		filter.add("a.a");
		test(filter, "a.a", true);
		test(filter, "a.b", false);
		test(filter, "b.a", false);

		filter = IGNORE_BY_NAME_FILTER();
		filter.add("a.a");
		test(filter, "a.a", false);
		test(filter, "a.b", true);
		test(filter, "b.a", true);

		filter = ACCEPT_BY_EXTENSION_FILTER();
		filter.add("a");
		test(filter, "a.a", true);
		test(filter, "a.b", false);
		test(filter, "b.a", true);

		filter = IGNORE_BY_EXTENSION_FILTER();
		filter.add("a");
		test(filter, "a.a", false);
		test(filter, "a.b", true);
		test(filter, "b.a", false);

		filter = ACCEPT_BY_NAME_FILTER();
		filter.add("*a");
		test(filter, "a.a", true);
		test(filter, "a.b", false);
		test(filter, "b.a", true);
		test(filter, "ab.a", true);
		test(filter, "ab.b", false);
	}

	private static void test(CFileFilter filter, String value, boolean expected) {
		boolean result = filter.acceptInternal(new CPath(value));
		if (result == expected) {
			System.out.println("PASS: " + value);
		} else {
			System.out.println("FAIL: " + value);
		}
	}
}
