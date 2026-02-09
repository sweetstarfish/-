private static class Entry<E> {
     //当前节点对应值
    E element;
    //下一个节点
    Entry<E> next;
    //上一个节点
    Entry<E> previous;
    Entry(E element, Entry<E> next, Entry<E> previous) {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }
}