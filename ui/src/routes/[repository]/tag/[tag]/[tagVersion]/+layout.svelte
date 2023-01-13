<script>
    import {fly} from 'svelte/transition';
    export let data;
</script>

<div class="body" in:fly={{y: 20}}>
    <div class="header">
        <div class="info">
            <img src={data.tagVersion.avatar}>

            <div>
                TagVersion <span class="id">{data.tagVersion.id}</span>
                <span class="user">by {data.tagVersion.user}</span>
            </div>
        </div>

        <div class="actions">
            <a class="history" href="history">history</a>
        </div>
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .body {
        border: 1px solid #383838;
        border-radius: 8px;
    }

    .header {
        border-radius: 8px 8px 0 0;
        background-color: #383838;
        margin: -1px;
        padding: .5em;
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
        text-decoration-color: crimson;
        text-decoration-thickness: 2px;
    }

    .user {
        color: #b3b3b3;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    .history {
        color: crimson !important;
    }

    .content {
        padding: .5em;
    }
</style>
