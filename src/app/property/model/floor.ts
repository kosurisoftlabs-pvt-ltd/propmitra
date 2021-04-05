import { Attachment } from "./attachment";

export class FloorModel {
    id: number;
    pId: number;
    propId: string;
    noFloors: number;
    noBeds: string;
    floorNo: number;
    areaSft: number;
    priceSft: number;
    attachments: Attachment[];
    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}