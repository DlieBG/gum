FROM node:18 as build

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm ci

COPY svelte.config.js .
COPY tsconfig.json .
COPY vite.config.ts .

COPY src src
COPY static static

RUN npm run build

FROM node:18

WORKDIR /app

COPY --from=build /app/package.json .
COPY --from=build /app/build build

ENTRYPOINT ["node", "build"]
