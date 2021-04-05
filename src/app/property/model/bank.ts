export class Banks {
    id: number;
    pId: number;
    propId: string;
    selectedBanks: any = [];

    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}