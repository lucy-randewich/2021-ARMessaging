package com.ajal.arsocialmessaging.ui.home;

import com.ajal.arsocialmessaging.ui.home.common.samplerender.Mesh;
import com.ajal.arsocialmessaging.ui.home.common.samplerender.SampleRender;
import com.ajal.arsocialmessaging.ui.home.common.samplerender.Shader;
import com.ajal.arsocialmessaging.ui.home.common.samplerender.Texture;
import com.ajal.arsocialmessaging.ui.home.common.samplerender.arcore.SpecularCubemapFilter;

import java.io.IOException;
import java.util.HashMap;

public class VirtualObjectRenderHelper {

    public static Mesh renderVirtualObjectMesh(SampleRender render, VirtualMessage virtualMessage) throws IOException {

        // Note: throws IOException, which will be caught in onSurfaceCreated() in HomeFragment
        return Mesh.createFromAsset(render, virtualMessage.getObjfilename());
    }

    public static Shader renderVirtualObjectShader(
            SampleRender render, VirtualMessage virtualMessage, SpecularCubemapFilter cubemapFilter, Texture dfgTexture)
            throws IOException {

        Texture virtualObjectAlbedoTexture =
                Texture.createFromAsset(
                        render,
                        virtualMessage.getAlbedoTexture(),
                        Texture.WrapMode.CLAMP_TO_EDGE,
                        Texture.ColorFormat.SRGB);
        Texture virtualObjectPbrTexture =
                Texture.createFromAsset(
                        render,
                        virtualMessage.getPbrTexture(),
                        Texture.WrapMode.CLAMP_TO_EDGE,
                        Texture.ColorFormat.LINEAR);

        return Shader.createFromAssets(
                    render,
                    "shaders/environmental_hdr.vert",
                    "shaders/environmental_hdr.frag",
                    /*defines=*/ new HashMap<String, String>() {
                        {
                            put(
                                    "NUMBER_OF_MIPMAP_LEVELS",
                                    Integer.toString(cubemapFilter.getNumberOfMipmapLevels()));
                        }
                    })
                    .setTexture("u_AlbedoTexture", virtualObjectAlbedoTexture)
                    .setTexture("u_RoughnessMetallicAmbientOcclusionTexture", virtualObjectPbrTexture)
                    .setTexture("u_Cubemap", cubemapFilter.getFilteredCubemapTexture())
                    .setTexture("u_DfgTexture", dfgTexture);
    }

}
