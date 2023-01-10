import {TagVersionService} from "$lib/services/tagVersion.service";

export const load = async ({ params }) => {
    return {
        tagVersion: await TagVersionService.getTagVersion(params.repository, params.tagVersion)
    }
}
