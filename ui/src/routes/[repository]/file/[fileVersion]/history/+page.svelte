<script>
    import {diffIds} from "./stores";
    import {onDestroy, onMount} from "svelte";

    export let data;

    let clearDiffIds = () => {
        diffIds.set([]);
    };

    onMount(clearDiffIds);
    onDestroy(clearDiffIds);

    function toTimestamp(id) {
        return new Date(parseInt(id.substr(0, 8), 16) * 1000).toLocaleString('de-DE', {
            day: '2-digit',
            month: '2-digit',
            year: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
</script>

<div class="body">
    {#each data.fileVersions as fileVersion, index (fileVersion.id)}
        <div class="version">
            <div class="info">
                <img src={fileVersion.avatar}>

                <a class="id" href="../{fileVersion.id}">{fileVersion.id}</a>

                <span class="user">by {fileVersion.user}</span>
            </div>

            {#if index == 0}
                <span class="latest">latest</span>
            {/if}

            {#if fileVersion.id == data.fileVersion.id}
                <span class="current">current</span>
            {/if}

            <div>
                <span class="timestamp">
                    {toTimestamp(fileVersion.id)}

                    <input type=checkbox disabled={fileVersion.deleted || ['png', 'jpg', 'gif', 'pdf'].includes(data.fileVersion.fileName.split('.')[1])} bind:group={$diffIds} value={fileVersion.id}>
                </span>

            </div>
        </div>

        {#if index !== data.fileVersions.length - 1}
            <div class="divider">
                <div class="hr"></div>
            </div>
        {/if}
    {/each}
</div>

<style>
    .version {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .info {
        display: flex;
        align-items: center;
    }

    img {
        width: 25px;
        margin-right: .75em;
    }

    .id {
        transition: 0.3s;
        text-decoration-color: #0000;
    }

    .id:hover {
        opacity: 1;
        text-decoration: underline;
        text-decoration-color: cornflowerblue;
        text-decoration-thickness: 2px;
    }

    .user {
        color: #b3b3b3;
        margin-left: .75em;
    }

    .latest {
        color: #f2751b;
    }

    .current {
        color: #e4f21b;
    }

    .timestamp {
        color: #999;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .divider {
        height: 15px;
        display: flex;
        align-items: center;
        width: calc(100% + 1em);
        margin-left: -.5em;
    }

    .hr {
        border-top: 1px solid #383838;
        width: 100%;
        margin-top: 3px;
    }
</style>
