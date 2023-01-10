import {RepositoryService} from "$lib/services/repository.service";
import {url} from "$lib/services/url.service";

export const load = async ({ params }) => {
    return {
        repository: await RepositoryService.getRepository(params.repository),
        api: url
    }
}
