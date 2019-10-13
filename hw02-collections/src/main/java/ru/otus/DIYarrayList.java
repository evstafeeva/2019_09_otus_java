package ru.otus;

import java.util.*;

public class DIYarrayList<T> implements List<T> {

    private T[] array;
    private int size = 0;
    private int indexOfTailElement = 0;

    public DIYarrayList(){
        array = (T[]) new Object[10];
        size = 10;
    }

    public int size() {
        return this.indexOfTailElement;
    }

    public boolean isEmpty() {
        return this.indexOfTailElement == 0;
    }

    public boolean contains(Object o) {
        for (int index = 0; index < indexOfTailElement; index++){
            if (array[index].equals(o)){
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return this.listIterator();
    }

    public Object[] toArray() {
        return Arrays.copyOfRange(array, 0, indexOfTailElement);
    }

    public <T1> T1[] toArray(T1[] t1s) {
        throw new UnsupportedOperationException();
    }

    public boolean add(T t) {
        if(indexOfTailElement==size)
        {
            T[] newArray = (T[]) new Object[size*2];
            for(int i = 0; i < size; i++){
                newArray[i] = array[i];
            }
            size *= 2;
            array = newArray;
        }
        array[indexOfTailElement] = t;
        indexOfTailElement++;
        return true;
    }

    public boolean remove(Object o) {
        if(indexOfTailElement == 0)
            return false;
        indexOfTailElement--;
        return true;
    }

    public boolean containsAll(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();
        while(iterator.hasNext())
        {
            if(!this.contains(iterator.next()))
                return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> collection) {
        Iterator<? extends T> iterator = collection.iterator();
        while(iterator.hasNext())
        {
            this.add(iterator.next());
        }
        return true;
    }

    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public T get(int i) {
        return array[i];
    }

    public T set(int i, T t) {
        T pastElement = array[i];
        array[i] = t;
        return pastElement;
    }

    public void add(int i, T t) {
        throw new UnsupportedOperationException();
    }

    public T remove(int i) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        for (int index = 0; index < indexOfTailElement; index++){
            if (array[index].equals(o)){
                return index;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int index = indexOfTailElement-1; index >=0; index--){
            if (array[index].equals(o)){
                return index;
            }
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }

    public ListIterator<T> listIterator(int i) {
        return new ListIterator<T>() {
            int index = i;
            int lastReturnedElement = -1;
            @Override
            public boolean hasNext() {
                return index<indexOfTailElement;
            }

            @Override
            public T next() {
                if(hasNext() == false)
                    throw new NoSuchElementException();
                lastReturnedElement = index;
                index++;
                return array[lastReturnedElement];
            }

            @Override
            public boolean hasPrevious() {
                return index > 0;
            }

            @Override
            public T previous() {
                if(hasPrevious() == false)
                    throw new NoSuchElementException();
                lastReturnedElement = index;
                index--;
                return array[lastReturnedElement];
            }

            @Override
            public int nextIndex() {
                return index++;
            }

            @Override
            public int previousIndex() {
                return index--;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(T t) {
                if(lastReturnedElement == -1)
                    throw new IllegalStateException();
                else
                    DIYarrayList.this.set(lastReturnedElement,t);
            }

            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public List<T> subList(int i, int i1) {
        throw new UnsupportedOperationException();
    }
}

