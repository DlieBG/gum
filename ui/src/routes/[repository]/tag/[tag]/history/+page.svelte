<script>
    export let data;

    $: tagVersions = data.tagVersions.reverse();

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
    {#each tagVersions as tagVersion, index (tagVersion.id)}
        <div class="version">
            <div class="info">
                <img src={tagVersion.avatar}>

                <a class="id" href={tagVersion.id}>{tagVersion.id}</a>
            </div>

            {#if index == 0}
                <span class="latest">latest</span>
            {/if}

            <span class="timestamp">{toTimestamp(tagVersion.id)}</span>
        </div>

        {#if index !== tagVersions.length - 1}
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
        text-decoration-color: crimson;
        text-decoration-thickness: 2px;
    }

    .latest {
        color: #f2751b;
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
