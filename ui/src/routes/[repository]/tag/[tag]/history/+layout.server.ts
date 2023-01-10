import {TagVersionService} from "$lib/services/tagVersion.service";

export const load = async ({ params }) => {
    return {
        tagVersions: await TagVersionService.getTagVersions(params.repository, params.tag)
    }
}
