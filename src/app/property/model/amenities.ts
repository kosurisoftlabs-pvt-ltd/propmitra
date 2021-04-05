export class Amenities {
    id: number;
    pId: number;
    propId: string;
    specification: any = [];

    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}