import {api} from "./api.service";
import {AvatarService} from "./avatar.service";

export class LockService {
    static async getLocks(repository: string) {
        return await fetch(`${api}/${repository}/lock`)
            .then(
                async (response) => {
                    return (await response.json()).map(
                        (lock: any) => {
                            return {
                                ...lock,
                                avatar: AvatarService.getLockAvatar(lock.id)
                            }
                        }
                    );
                }
            );
    }
}
