export class Nearby {
    id: number;
    pId: number;
    propId: string;
    hospitals: any = [];
    schools: any = [];
    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}