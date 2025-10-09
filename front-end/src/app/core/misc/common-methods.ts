export class CommonMethods {
    static random(min: number, max:number): number {
        return 1+Math.floor(Math.random()*(max-min));
    }
}