package com.bruno.ph_and_co.client;

import com.bruno.ph_and_co.blockentity.HeavyTankBlockEntity;
import com.bruno.ph_and_co.blockentity.HeavyTankBlockEntity.TankRenderData;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.ArrayList;
import java.util.List;

public class HeavyTankModel extends BakedModelWrapper<BakedModel> {

    public HeavyTankModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        List<BakedQuad> originalQuads = super.getQuads(state, side, rand, extraData, renderType);

        // PADRÃO 1x1 ISOLADO: Coluna 0, Linha 3 (Baseado na sua matriz!)
        int tx = 0;
        int ty = 3;

        if (extraData != null && extraData.has(HeavyTankBlockEntity.RENDER_DATA)) {
            TankRenderData data = extraData.get(HeavyTankBlockEntity.RENDER_DATA);
            if (data != null && side != null) {
                tx = getTextureX(side, data);
                ty = getTextureY(side, data);
            }
        }

        List<BakedQuad> processedQuads = new ArrayList<>(originalQuads.size());
        for (BakedQuad quad : originalQuads) {
            processedQuads.add(sliceQuad(quad, tx, ty));
        }

        return processedQuads;
    }

    private int getTextureX(Direction side, TankRenderData data) {
        boolean connLeft = false, connRight = false;

        // Compensação do Espelhamento UV do Motor Gráfico
        switch (side) {
            case NORTH -> { connLeft = data.west();  connRight = data.east(); }
            case SOUTH -> { connLeft = data.east();  connRight = data.west(); }
            case WEST  -> { connLeft = data.south(); connRight = data.north(); }
            case EAST  -> { connLeft = data.north(); connRight = data.south(); }
            case UP, DOWN -> { connLeft = data.west(); connRight = data.east(); }
        }

        // MATRIZ EXATA: Colunas [X]
        if (!connLeft && !connRight) return 0; // [0] Unitária / Largura 1
        if (!connLeft) return 3;               // [3] Borda Esquerda (Invertida pro CTM encaixar)
        if (!connRight) return 1;              // [1] Borda Direita (Invertida pro CTM encaixar)
        return 2;                              // [2] Miolo central conectado
    }

    private int getTextureY(Direction side, TankRenderData data) {
        boolean connTop = false, connBottom = false;

        switch (side) {
            case NORTH, SOUTH, WEST, EAST -> { connTop = data.up(); connBottom = data.down(); }
            case UP -> { connTop = data.north(); connBottom = data.south(); }
            case DOWN -> { connTop = data.south(); connBottom = data.north(); }
        }

        // MATRIZ EXATA: Linhas [Y]
        if (!connTop && !connBottom) return 3; // [3] Unitária / Altura 1
        if (!connTop) return 0;                // [0] Borda Topo
        if (!connBottom) return 2;             // [2] Borda Fundo
        return 1;                              // [1] Miolo central
    }

    private BakedQuad sliceQuad(BakedQuad quad, int tx, int ty) {
        TextureAtlasSprite sprite = quad.getSprite();
        if (sprite == null) return quad;

        float minU = sprite.getU0(), maxU = sprite.getU1();
        float minV = sprite.getV0(), maxV = sprite.getV1();
        float spriteWidth = maxU - minU, spriteHeight = maxV - minV;
        float tileWidth = spriteWidth / 4f, tileHeight = spriteHeight / 4f;

        float tileStartU = minU + (tx * tileWidth);
        float tileStartV = minV + (ty * tileHeight);

        int[] vertices = quad.getVertices().clone();
        for (int i = 0; i < 4; i++) {
            float u = Float.intBitsToFloat(vertices[i * 8 + 4]);
            float v = Float.intBitsToFloat(vertices[i * 8 + 5]);

            float normU = (u - minU) / spriteWidth;
            float normV = (v - minV) / spriteHeight;

            vertices[i * 8 + 4] = Float.floatToRawIntBits(tileStartU + (normU * tileWidth));
            vertices[i * 8 + 5] = Float.floatToRawIntBits(tileStartV + (normV * tileHeight));
        }

        return new BakedQuad(vertices, quad.getTintIndex(), quad.getDirection(), sprite, quad.isShade());
    }
}