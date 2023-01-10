import {redirect} from "@sveltejs/kit";
import type {PageServerLoadEvent} from "./$types";
import {RepositoryService} from "$lib/services/repository.service";
import { TagVersionService } from "$lib/services/tagVersion.service";

export const load = async ({ params }: PageServerLoadEvent) => {
    let repository = await RepositoryService.getRepository(params.repository);

    throw redirect(301, `/${params.repository}/tag/${params.tag}/${(await TagVersionService.getTagVersions(params.repository, params.tag))[0].id}`);
};
