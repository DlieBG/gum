import {url} from "./url.service";
import {AvatarService} from "./avatar.service";

export class RepositoryService {
    static async getRepositories() {
        return await fetch(`${url}`)
            .then(
                async (response) => {
                    return (await response.json()).map(
                        (repository: any) => {
                            return {
                                ...repository,
                                avatar: AvatarService.getRepositoryAvatar(repository.id)
                            }
                        }
                    );
                }
            );
    }

    static async getRepository(name: string) {
        return await fetch(`${url}/${name}`)
            .then(
                async (response) => {
                    let repository = await response.json()

                    return {
                        ...repository,
                        avatar: AvatarService.getRepositoryAvatar(repository.id),
                        tagNames: repository.tagVersions
                            .map((tV: any) => tV.tagName)
                            .filter((value, index, self) => self.indexOf(value) === index)
                            .sort()
                    }
                }
            );
    }

    static async createRepository(name: string) {
        return await fetch(`${url}/${name.toLowerCase()}`, {
            method: 'POST'
        }).then(
            async (response) => {
                return await response.json()
            }
        );
    }
}
