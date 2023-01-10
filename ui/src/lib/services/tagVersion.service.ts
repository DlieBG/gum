import {api} from "./api.service";
import {AvatarService} from "./avatar.service";

export class TagVersionService {
    static async getTagVersions(repository: string, name: string) {
        return await fetch(`${api}/${repository}/tagversion?tagName=${name}`)
            .then(
                async (response) => {
                    return (await response.json()).map(
                        (tagVersion: any) => {
                            return {
                                ...tagVersion,
                                avatar: AvatarService.getTagVersionAvatar(tagVersion.id)
                            }
                        }
                    );
                }
            );
    }

    static async getTagVersion(repository: string, id: string) {
        return await fetch(`${api}/${repository}/tagversion/${id}`)
            .then(
                async (response) => {
                    let tagVersion = await response.json()

                    return {
                        ...tagVersion,
                        avatar: AvatarService.getTagVersionAvatar(tagVersion.id)
                    }
                }
            );
    }
}
