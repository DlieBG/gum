<script>
    export let data;
</script>

<div class="body">
    {#each data.locks as lock, index (lock.id)}
        <div class="lock">
            <div class="info">
                <img src={lock.avatar}>

                <span class="id">{lock.id}</span>

                <span class="user">by {lock.user}</span>
            </div>

            <div class="wrapper">
                {#if lock.fileNameRegex}
                    <span class="regex">File Prefix: {lock.fileNameRegex}</span>
                {/if}

                {#if lock.tagNameRegex}
                    <span class="regex">Tag Prefix: {lock.tagNameRegex}</span>
                {/if}
            </div>
        </div>

        {#if index !== data.locks.length - 1}
            <div class="divider">
                <div class="hr"></div>
            </div>
        {/if}
    {/each}
</div>

<style>
    .lock {
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
        text-decoration: underline;
        text-decoration-color: #f21b95;
        text-decoration-thickness: 2px;
    }

    .user {
        color: #b3b3b3;
        margin-left: .75em;
    }

    .wrapper {
        display: flex;
        flex-direction: column;
    }

    .regex {
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
