export class Stat {

    private statValue: number;
    private statOptions: number[];
    private prevStatValue: number;

    constructor() {
        this.statValue = 0;
        this.prevStatValue = 0;
        this.statOptions = [];
    }
    
    public get value(): number {
        return this.statValue;
    }

    public set value(v : number) {
        this.statValue = v;
    }

    
    public get options(): number[] {
        return this.statOptions;
    }
    
    public get prevValue() : number {
        return this.prevStatValue;
    }
    
    public set prevValue(v : number) {
        this.prevStatValue = v;
    }
}