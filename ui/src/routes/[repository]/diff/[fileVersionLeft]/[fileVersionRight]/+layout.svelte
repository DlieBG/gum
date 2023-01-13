<script>
    export let data;

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
    <div class="header">
        <h3>{data.diffFileVersions[0].fileName}</h3>
    </div>

    <div class="content">
        <div class="info">
            {#each data.diffFileVersions as fileVersion (fileVersion.id)}
                <div class="version">
                    <img src={fileVersion.avatar}>

                    <div class="wrapper">
                        <span class="id">{fileVersion.id}</span>
                        <span class="user">by {fileVersion.user}</span>
                        <span class="timestamp">{toTimestamp(fileVersion.id)}</span>
                    </div>
                </div>
            {/each}
        </div>

        <div class="diff">
            <slot/>
        </div>
    </div>
</div>

<style>
    .header {
        margin-top: 1em;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .content {
        border: 1px solid #383838;
        border-radius: 8px;
        margin-top: 1em;
    }

    .info {
        border-radius: 8px 8px 0 0;
        background-color: #383838;
        margin: -1px;
        padding: .5em;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .version {
        width: 50%;
        display: flex;
        align-items: center;
    }

    img {
        width: 25px;
        margin-right: .75em;
    }

    .wrapper {
        display: flex;
        flex-direction: column;
    }

    .id {
        text-decoration: underline;
        text-decoration-color: cornflowerblue;
        text-decoration-thickness: 2px;
    }

    .user {
        color: #b3b3b3;
    }

    .timestamp {
        color: #999;
    }

    .diff {
        background-color: #1e1e1e;
        border-radius: 0 0 8px 8px;
    }
</style>
