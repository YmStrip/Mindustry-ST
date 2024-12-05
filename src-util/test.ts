//@ts-nocheck
const api = new class {
    playA(a: any[], key: number) {
        let left = 0;
        let right = a.length - 1;
        while (left <= right) {
            const mid = Math.floor((left + right) / 2)
            if (a[mid] == key) {
                return [key]
            } else if (a[mid] < key) {
                left = mid + 1
            } else if (a[mid] > key) {
                right = mid - 1
            }
        }
        return [left, right]
    }
}
for (let i = -5; i < 15; ++i) {
    const g = [0.1, 1, 2, 3, 4, 5.5]
    console.log(i / 2, api.playA(g, i / 2), g)
}