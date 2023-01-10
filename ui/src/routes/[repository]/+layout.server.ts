import {RepositoryService} from "$lib/services/repository.service";
import {publicApi, api} from "$lib/services/api.service";

export const load = async ({ params }) => {
    return {
        repository: await RepositoryService.getRepository(params.repository),
        api: api,
        publicApi: publicApi
    }
}
