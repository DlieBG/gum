<script>
    const gowl = ' /\\ /\\\n' + '((ovo))\n' + '():::()\n' + '  VVV';
</script>

<div class="body">
    <div class="header">
        <a href="/">
            <h1>gum</h1>
        </a>

        <a href="https://github.com/DlieBG/gum" target="_blank">
            <pre>{gowl}</pre>
        </a>
    </div>

    <div class="content">
        <div class="slot">
            <slot/>
        </div>
    </div>
</div>

<style>
    .header {
        margin: .5em;
        border: 1px solid #a25e2d;
        border-radius: 12px;
        background-color: #a25e2d;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    h1 {
        margin: .25em .5em .5em .5em;
    }

    a, a:visited {
        text-decoration: none;
        color: #fff;
    }

    pre {
        margin-right: 1em;
    }

    .content {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .slot {
        max-width: 1000px;
        width: 100%;
    }
</style>
