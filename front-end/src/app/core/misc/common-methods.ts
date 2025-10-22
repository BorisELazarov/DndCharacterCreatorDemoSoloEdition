export class CommonMethods {
    static random(min: number, max: number): number {
        return 1 + Math.floor(Math.random() * (max - min));
    }

    static removeDuplicates(list: any[]): any[] {
        return list.filter((el, i, a) => i === a.indexOf(el));
    }
}