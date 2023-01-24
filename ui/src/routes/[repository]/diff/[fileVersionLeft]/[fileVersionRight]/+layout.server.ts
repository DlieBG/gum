import type {PageServerLoadEvent} from "./$types";
import {FileVersionService} from "$lib/services/fileVersion.service";

export const load = async ({params}: PageServerLoadEvent) => {
    return {
        diffFileVersions: [
            await FileVersionService.getFileVersion(params.repository, params.fileVersionRight),
            await FileVersionService.getFileVersion(params.repository, params.fileVersionLeft)
        ]
    };
}
