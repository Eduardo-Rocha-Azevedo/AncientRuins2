package principal;

import entity.Entity;
import tile_interactive.InteractiveTile;

import java.awt.Rectangle;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // CHECAGEM DE COLISÃO COM TILES
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisioOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisioOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisioOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisioOn = true;
                }
                break;
        }
    }

    // CHECAGEM DE COLISÃO COM OBJETOS (como árvores grandes)
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {

                // cria retângulos temporários para colisão
                Rectangle entityRect = new Rectangle(
                        entity.worldX + entity.solidArea.x,
                        entity.worldY + entity.solidArea.y,
                        entity.solidArea.width,
                        entity.solidArea.height);

                Rectangle objRect = new Rectangle(
                        gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x,
                        gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y,
                        gp.obj[gp.currentMap][i].solidArea.width,
                        gp.obj[gp.currentMap][i].solidArea.height);

                // desloca entity conforme direção e velocidade
                switch (entity.direction) {
                    case "up":
                        entityRect.y -= entity.speed;
                        break;
                    case "down":
                        entityRect.y += entity.speed;
                        break;
                    case "left":
                        entityRect.x -= entity.speed;
                        break;
                    case "right":
                        entityRect.x += entity.speed;
                        break;
                }

                // verifica colisão
                if (entityRect.intersects(objRect)) {
                    if (gp.obj[gp.currentMap][i].collisioOn) {
                        entity.collisioOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        return index;
    }

    // CHECAGEM DE COLISÃO ENTRE ENTIDADES (NPCs, monstros)
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null && target[gp.currentMap][i] != entity) {

                Rectangle entityRect = new Rectangle(
                        entity.worldX + entity.solidArea.x,
                        entity.worldY + entity.solidArea.y,
                        entity.solidArea.width,
                        entity.solidArea.height);

                Rectangle targetRect = new Rectangle(
                        target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x,
                        target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y,
                        target[gp.currentMap][i].solidArea.width,
                        target[gp.currentMap][i].solidArea.height);

                switch (entity.direction) {
                    case "up":
                        entityRect.y -= entity.speed;
                        break;
                    case "down":
                        entityRect.y += entity.speed;
                        break;
                    case "left":
                        entityRect.x -= entity.speed;
                        break;
                    case "right":
                        entityRect.x += entity.speed;
                        break;
                }

                if (entityRect.intersects(targetRect)) {
                    entity.collisioOn = true;
                    index = i;
                }
            }
        }

        return index;
    }

    // CHECAGEM DE COLISÃO COM PLAYER
    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        Rectangle entityRect = new Rectangle(
                entity.worldX + entity.solidArea.x,
                entity.worldY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height);

        Rectangle playerRect = new Rectangle(
                gp.player.worldX + gp.player.solidArea.x,
                gp.player.worldY + gp.player.solidArea.y,
                gp.player.solidArea.width,
                gp.player.solidArea.height);

        switch (entity.direction) {
            case "up":
                entityRect.y -= entity.speed;
                break;
            case "down":
                entityRect.y += entity.speed;
                break;
            case "left":
                entityRect.x -= entity.speed;
                break;
            case "right":
                entityRect.x += entity.speed;
                break;
        }

        if (entityRect.intersects(playerRect)) {
            entity.collisioOn = true;
            contactPlayer = true;
        }

        return contactPlayer;
    }

  /*   public boolean checkItHitBox(Entity entity, InteractiveTile[][] iTile) {
        // Hitbox da entidade
        int entityLeft = entity.worldX + entity.solidArea.x;
        int entityTop = entity.worldY + entity.solidArea.y;
        int entityRight = entityLeft + entity.solidArea.width;
        int entityBottom = entityTop + entity.solidArea.height;

        // Hitbox da copa do tile
        Rectangle crownArea = iTile[gp.currentMap][0].crownArea; // Replace with correct index or logic
        int crownLeft = iTile[gp.currentMap][0].worldX + crownArea.x;
        int crownTop = iTile[gp.currentMap][0].worldY + crownArea.y;
        int crownRight = crownLeft + crownArea.width;
        int crownBottom = crownTop + crownArea.height;

        // Verifica colisão
        boolean collision = entityRight > crownLeft &&
                entityLeft < crownRight &&
                entityBottom > crownTop &&
                entityTop < crownBottom;

        return collision;
    }*/
}