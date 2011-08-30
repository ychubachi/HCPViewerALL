/*
 * CObservableList.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import clib.common.model.CAbstractModelObject;

/**
 * CObservableList
 */
public class CObservableList<T> extends CAbstractModelObject implements List<T> {

	private List<T> delegate;

	public CObservableList() {
		this.delegate = new ArrayList<T>();
	}

	public CObservableList(Collection<T> collection) {
		this.delegate = new ArrayList<T>(collection);
	}

	public CObservableList(List<T> delegate) {
		this.delegate = delegate;
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

	public int size() {
		return delegate.size();
	}

	public T set(int index, T element) {
		T result = delegate.set(index, element);
		this.fireModelUpdated();
		return result;
	}

	public boolean retainAll(Collection<?> c) {
		boolean result = delegate.retainAll(c);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public boolean removeAll(Collection<?> c) {
		boolean result = delegate.removeAll(c);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public T remove(int index) {
		T result = delegate.remove(index);
		this.fireModelUpdated();
		return result;
	}

	public boolean remove(Object o) {
		boolean result = delegate.remove(o);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public ListIterator<T> listIterator(int index) {
		return delegate.listIterator(index);
	}

	public ListIterator<T> listIterator() {
		return delegate.listIterator();
	}

	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public T get(int index) {
		return delegate.get(index);
	}

	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	public void clear() {
		delegate.clear();
		fireModelUpdated();
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		boolean result = delegate.addAll(index, c);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public boolean addAll(Collection<? extends T> c) {
		boolean result = delegate.addAll(c);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public void add(int index, T element) {
		delegate.add(index, element);
		this.fireModelUpdated();
	}

	public boolean add(T o) {
		boolean result = delegate.add(o);
		if (result) {
			this.fireModelUpdated();
		}
		return result;
	}

	public String toString() {
		return delegate.toString();
	}

}
