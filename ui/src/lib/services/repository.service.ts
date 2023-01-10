import {api} from "./api.service";
import {AvatarService} from "./avatar.service";

export class RepositoryService {
    static async getRepositories() {
        return await fetch(`${api}`)
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
        return await fetch(`${api}/${name}`)
            .then(
                async (response) => {
                    let repository = await response.json()
                    return {
                        ...repository,
                        avatar: AvatarService.getRepositoryAvatar(repository.id),
                        tagNames: repository.tags
                    }
                }
            );
    }

    static async createRepository(name: string) {
        return await fetch(`${api}/${name.toLowerCase()}`, {
            method: 'POST'
        }).then(
            async (response) => {
                return await response.json()
            }
        );
    }
}
