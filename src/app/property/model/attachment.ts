export class Attachment {
    id: number;
    fileName: string;
    fileType: string;
    data: any = [];

    constructor(values: Object = {}) {
        //Constructor initialization
        Object.assign(this, values);
    }
}