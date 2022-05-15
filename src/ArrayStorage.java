import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int MAX_SIZE = 10000;
    Resume[] storage = new Resume[MAX_SIZE];

    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    void save(Resume r) {
        if (size >= MAX_SIZE) return;

        for (int i = 0; i < size; i++) {
            if (r.getUuid() == storage[i].getUuid()) return;
        }

        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                System.arraycopy(storage, i + 1, storage, i, size - 1 - i);
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }
}
