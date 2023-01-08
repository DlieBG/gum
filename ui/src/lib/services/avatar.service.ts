export class AvatarService {
    private static getUrl(type: string, seed: string, radius: string) {
        return `https://avatars.dicebear.com/api/${type}/${seed}.svg?b=%23383838&r=${radius}`;
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
}
