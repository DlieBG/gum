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
    {#each data.tagVersion.fileVersions as fileVersion, index (fileVersion.id)}
        <div class="file {fileVersion.deleted ? 'deleted' : ''}">
            <a href="../../file/{fileVersion.id}" class="filename">{fileVersion.fileName}</a>

            <div class="wrapper">
                <a href="../../file/{fileVersion.id}/history" class="history">history</a>

                {#if !fileVersion.deleted}
                    <a href="{data.api}/{data.repository.name}/fileversion/{fileVersion.id}/file" target="_blank"
                       class="download">download</a>
                {/if}

                <span class="timestamp">{toTimestamp(fileVersion.id)}</span>
            </div>
        </div>

        {#if index !== data.tagVersion.fileVersions.length - 1}
            <div class="divider">
                <div class="hr"></div>
            </div>
        {/if}
    {/each}
</div>

<style>
    .file {
        width: 100%;
        display: flex;
        justify-content: space-between;
    }

    .deleted {
        opacity: .25;
    }

    .filename {
        transition: 0.3s;
        text-decoration-color: #0000;
    }

    .filename:hover {
        opacity: 1;
        text-decoration: underline;
        text-decoration-color: cornflowerblue;
        text-decoration-thickness: 2px;
    }

    .timestamp {
        color: #999;
    }

    .wrapper {
        width: 40%;
        display: flex;
        justify-content: space-between;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .download {
        color: #f2751b !important;
    }

    .history {
        color: cornflowerblue !important;
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
