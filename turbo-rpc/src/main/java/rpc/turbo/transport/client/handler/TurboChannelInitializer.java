package rpc.turbo.transport.client.handler;

import static rpc.turbo.config.TurboConstants.MAX_FRAME_LENGTH;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import rpc.turbo.serialization.Serializer;
import rpc.turbo.transport.client.codec.RequestEncoder;
import rpc.turbo.transport.client.codec.ResponseDecoder;
import rpc.turbo.transport.client.future.FutureContainer;

public class TurboChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final Serializer serializer;

	public TurboChannelInitializer(Serializer serializer) {
		this.serializer = serializer;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		FutureContainer container = new FutureContainer();
		RequestEncoder encoder = new RequestEncoder(serializer, container);
		ResponseDecoder decoder = new ResponseDecoder(MAX_FRAME_LENGTH, serializer, container);

		ch.pipeline().addLast("encoder", encoder).addLast("decoder", decoder);
	}
}
