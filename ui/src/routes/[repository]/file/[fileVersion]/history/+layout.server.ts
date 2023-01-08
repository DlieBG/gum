import {FileVersionService} from "$lib/services/fileVersion.service";

export const load = async ({params}) => {
    let fileVersion = await FileVersionService.getFileVersion(params.repository, params.fileVersion)

    return {
        fileVersions: await FileVersionService.getFileVersions(params.repository, fileVersion.fileName)
    }
};
