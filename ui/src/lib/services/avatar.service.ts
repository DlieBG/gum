export class AvatarService {
    private static getUrl(type: string, seed: string, radius: string) {
        return `https://avatars.dicebear.com/api/${type}/${seed}.svg?b=%23383838&r=${radius}`;
    }

    private static getNewUrl(type: string, seed: string, radius: string) {
        return `https://api.dicebear.com/5.x/${type}/svg?seed=${seed}&radius=${radius}`;
    }

    static getRepositoryAvatar(id: string) {
        return this.getUrl('micah', id, '32');
    }

    static getTagVersionAvatar(id: string) {
        return this.getUrl('identicon', id, '12');
    }

    static getFileVersionAvatar(id: string) {
        return this.getUrl('adventurer-neutral', id, '12');
    }

    static getLockAvatar(id: string) {
        return this.getNewUrl('bottts-neutral', id, '12');
    }
}
