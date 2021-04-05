import { Attachment } from "./attachment";

export class BasicModel {
    id: number;
    pId: number;
    propId: string;
    postedBy: string;
    rentSale: string;
    propAge: string;
    propType: string;
    propStatus: string;
    completion: string;
    completionType: string;
    leaseTerm: string;
    advanceAmount: number;
    rentAmount: number;
    furnishedOrSemi: string;
    noBathRooms: string;
    landMark: string;
    placesNear: string;
    floorSft: string;
    floorNo: string;
    projectName: string;
    propLocation: string;
    pinCode: string;
    description: string;
    isGated: string;
    frontImage: Attachment;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}