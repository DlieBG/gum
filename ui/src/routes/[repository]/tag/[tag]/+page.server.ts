import {redirect} from "@sveltejs/kit";
import type {PageServerLoadEvent} from "./$types";
import {RepositoryService} from "$lib/services/repository.service";

export const load = async ({ params }: PageServerLoadEvent) => {
    let repository = await RepositoryService.getRepository(params.repository);

    throw redirect(301, `/${params.repository}/tag/${params.tag}/${repository.tagVersions.reverse().filter((tagVersion) => tagVersion.tagName == params.tag)[0].id}`);
};
