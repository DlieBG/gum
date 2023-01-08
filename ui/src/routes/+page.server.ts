import {RepositoryService} from "../lib/services/repository.service";

export const load = async () => {
    return {
        repositories: await RepositoryService.getRepositories(),
    }
};
