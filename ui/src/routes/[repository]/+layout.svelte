<script>
    export let data;
</script>

<div class="body">
    <div class="header">
        <img src={data.repository.avatar}>
        <h2>
            Repository
            <span class="name">{data.repository.name}</span>
        </h2>
    </div>

    <div class="content">
        <slot />
    </div>
</div>

<style>
    .body {
        margin: 1em;
    }

    .header {
        display: flex;
        align-items: center;
    }

    img {
        width: 50px;
    }

    h2 {
        margin: 1em;
    }

    .name {
        text-decoration: underline;
        text-decoration-color: forestgreen;
        text-decoration-thickness: 3px;
    }
</style>
