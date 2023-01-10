<script>
    import {page} from '$app/stores';

    function back() {
        history.back();
    }
</script>

<div class="body">
    <h1>{$page.status}</h1>

    <p>
        {$page.error.message}
    </p>

    <a href="" on:click={back}>Back</a>
</div>

<style>
    .body {
        margin: 1em;
    }

    p {
        margin-bottom: 3em;
    }

    a, a:visited {
        text-decoration: none;
        color: #f2751b;
    }
</style>
