import type {PageServerLoadEvent} from "./$types";
import {FileVersionService} from "$lib/services/fileVersion.service";

export const load = async ({params}: PageServerLoadEvent) => {
    return {
        fileVersion: await FileVersionService.getFileVersion(params.repository, params.fileVersion),
    };
}
