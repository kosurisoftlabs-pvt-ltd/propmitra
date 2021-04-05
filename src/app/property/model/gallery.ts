import { Attachment } from "./attachment";

export class Gallery {
    id: number;
    pId: number;
    propId: string;
    attachments: Attachment[];
    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}