class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;

    public CustomHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public V get(K key) {
        var node = table[getIndex(key)];
        while (node != null) {
            if (node.hash == hash(key) &&
                    (node.key == key || (key != null) && Objects.equals(key, node.key)))
                return node.value;
            node = node.next;
        }
        return null;
    }

    public void put(K key, V value) {
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        var index = getIndex(key);

        if (get(key) == value) {
            return;
        }

        // Если ключи совпадают, но разные значения - перезапись без увеличения size
        if (get(key) != null) {
            table[index] = new Node<>(hash(key), key, value, table[index]);
            return;
        }

        table[index] = new Node<>(hash(key), key, value, table[index]);
        size++;
    }

    public V remove(K key) {
        var index = getIndex(key);
        var node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if ((node.hash == hash(key)) && (key == null && node.key == null) ||
                    (key != null && key.equals(node.key))) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    private void resize() {
        var oldTable = table;
        var newCapacity = oldTable.length * 2;
        table = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> node : oldTable) {
            while (node != null) {
                var nextNode = node.next;
                var newIndex = getIndex(node.key);

                node.next = table[newIndex];
                table[newIndex] = node;

                node = nextNode;
            }
        }
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : key.hashCode();
    }

    private final int getIndex(K key) {
        if (key == null)
            return 0;
        return (table.length - 1) & hash(key);
    }

    public static void main(String[] args) {
        var customHashMap = new CustomHashMap<String, Integer>();

        customHashMap.put("Key#1", 24);
        customHashMap.put("Key#2", 12);
        customHashMap.put("Key#3", 11);
        customHashMap.put("Key#2", 11);
        customHashMap.put("Key#8", 15);
        customHashMap.remove("Key#1");
    }
}
