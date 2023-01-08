import {url} from "./url.service";
import {AvatarService} from "./avatar.service";

export class FileVersionService {
    static async getFileVersions(repository: string, fileName: string) {
        return await fetch(`${url}/${repository}/fileversion?fileName=${fileName}`)
            .then(
                async (response) => {
                    return (await response.json()).map(
                        (fileVersion: any) => {
                            return {
                                ...fileVersion,
                                avatar: AvatarService.getFileVersionAvatar(fileVersion.id)
                            }
                        }
                    );
                }
            );
    }

    static async getFileVersion(repository: string, id: string) {
        return await fetch(`${url}/${repository}/fileversion/${id}`)
            .then(
                async (response) => {
                    let fileVersion = await response.json()

                    return {
                        ...fileVersion,
                        avatar: AvatarService.getFileVersionAvatar(fileVersion.id)
                    }
                }
            );
    }
}
