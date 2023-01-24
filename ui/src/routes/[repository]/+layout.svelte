<script>
    export let data;
</script>

<div class="body">
    <div class="header">
        <div class="info">
            <img src={data.repository.avatar}>
            <h2>
                Repository
                <a class="name" href="/{data.repository.name}">{data.repository.name}</a>
            </h2>
        </div>

        <a class="locks" href="/{data.repository.name}/lock">locks</a>
    </div>

    <div class="content">
        <slot/>
    </div>
</div>

<style>
    .body {
        margin: 1em;
    }

    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .info {
        display: flex;
        align-items: center;
    }

    img {
        width: 50px;
    }

    h2 {
        margin: 1em;
        width: 100%;
    }

    .name, .name:visited {
        text-decoration: underline;
        text-decoration-color: forestgreen;
        text-decoration-thickness: 3px;
    }

    .locks {
        color: #f21b95 !important;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }
</style>
