export class Material {
    id: number;
    pId: number;
    propId: string;
    doors: string;
    windows: string;
    cupboards: string;
    wallPaint: string;
    floor: string;
    kitchen: string;
    wiring: string;
    toilet: string;
    locks: string;
    electricalSwitches: string;
    waterPipes: string;
    sink: string;
    washBasin: string;

    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}