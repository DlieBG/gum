import {redirect, error} from "@sveltejs/kit";
import {RepositoryService} from "$lib/services/repository.service";

export const actions = {
    default: async ({request}) => {
        const data = await request.formData();

        const repository = await RepositoryService.createRepository(data.get('name'));

        if (repository.error)
            throw error(repository.status, repository.error);

        throw redirect(302, `/${repository.name}/tag/main`);
    }
};
